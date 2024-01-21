package cc.newex.commons.support.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author newex-team
 * @date 2017/12/28
 */
@Slf4j
@Component
@ConditionalOnClass(PlatformTransactionManager.class)
public class TransactionUtils {
    @Autowired
    private PlatformTransactionManager transactionManager;

    public <V> V runInTransaction(final Transaction<V> transaction) {
        return this.runInTransaction(transaction, new DefaultTransactionDefinition());
    }

    public <V> V runInTransaction(final Transaction<V> transaction, final TransactionDefinition definition) {
        final TransactionStatus status = this.transactionManager.getTransaction(definition);
        try {
            final V v = transaction.exec();
            this.transactionManager.commit(status);
            return v;
        } catch (final Exception e) {
            this.transactionManager.rollback(status);
            log.error("transaction error:{}", e.getMessage());
            return null;
        }
    }
}