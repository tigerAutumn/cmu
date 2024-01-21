package cc.newex.maker.perpetual.core;

import cc.newex.maker.perpetual.enums.MakerEnum;
import cc.newex.maker.perpetual.enums.PlatformEnum;
import cc.newex.maker.utils.SimpleThreadUtils;
import cc.newex.openapi.bitmex.v1.BitMexV1RestApi;
import cc.newex.openapi.cmx.client.CmxClient;
import cc.newex.openapi.cmx.perpetual.domain.DepthOrderBook;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;

/**
 * 拉取深度
 *
 * @author cmx-sdk-team
 * @date 2018/04/28
 */
@Slf4j
public class FetchDepth {

    private TaskExecutor taskExecutor;

    private MakerEnum makerEnum;

    private DoTrade doTrade;
    private DoDepth doDepth;

    private OrderService robotOrderService;

    private BitMexV1RestApi bitMexV1RestApi;

    private volatile DepthOrderBook cmxBook;
    private volatile DepthOrderBook otherBook;

    public FetchDepth(final TaskExecutor taskExecutor, final MakerEnum makerEnum, final DoTrade doTrade, final DoDepth doDepth,
                      final CmxClient cmxClient, final BitMexV1RestApi bitMexV1RestApi) {
        this.taskExecutor = taskExecutor;
        this.makerEnum = makerEnum;
        this.doTrade = doTrade;
        this.doDepth = doDepth;
        this.robotOrderService = cmxClient.perpetual().ccex().order();
        this.bitMexV1RestApi = bitMexV1RestApi;
    }

    public void execute() {
        this.taskExecutor.execute(() -> {
            while (true) {
                try {
                    this.bitMexDepth();
                } catch (final Exception e) {
                    log.error("fetch other exchange depth error", e);
                }

                SimpleThreadUtils.sleep(5000);
            }
        });

        this.taskExecutor.execute(() -> {
            while (true) {
                try {
                    this.cmxDepth();
                } catch (final Exception e) {
                    log.error("fetch coinmex depth error", e);
                }

                SimpleThreadUtils.sleep(1000);
            }
        });

        this.taskExecutor.execute(() -> {
            while (true) {
                try {
                    if (this.cmxBook != null && this.otherBook != null) {
                        log.info("fetchDepth, code: {}", this.makerEnum);

                        final long start = System.currentTimeMillis();

                        final DepthOrderBook cmxBookClone = this.cmxBook.clone();
                        final DepthOrderBook otherBookClone = this.otherBook.clone();

                        // 机器人成交做量
                        this.doTrade.execute(cmxBookClone, otherBookClone);

                        //做深度(有做深度账号才做深度)
                        this.doDepth.execute(cmxBookClone, otherBookClone);

                        synchronized (this) {
                            this.cmxBook = null;
                            this.otherBook = null;
                        }

                        log.info("fetchDepth, code: {}, total times(s): {}", this.makerEnum, (System.currentTimeMillis() - start) / 1000);
                    }
                } catch (final Exception e) {
                    log.error("FetchDepth execute error, code: " + this.makerEnum, e);

                }

                SimpleThreadUtils.sleep(5000);
            }
        });
    }

    private void bitMexDepth() {
        if (this.makerEnum.getPlatform() != PlatformEnum.BITMEX) {
            return;
        }

        synchronized (this) {
            final String code = this.makerEnum.getAlias();

            this.otherBook = this.bitMexV1RestApi.depth(code, 100);
        }
    }

    private void cmxDepth() throws Exception {
        synchronized (this) {
            this.cmxBook = this.robotOrderService.depth(this.makerEnum.name().toLowerCase(), 100);

            if (this.cmxBook == null) {
                this.cmxBook = new DepthOrderBook();
            }
        }
    }
}
