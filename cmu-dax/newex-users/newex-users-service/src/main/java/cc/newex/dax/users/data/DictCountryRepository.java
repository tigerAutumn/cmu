package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.DictCountryExample;
import cc.newex.dax.users.domain.DictCountry;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 全球国家(地区)表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-08
 */
@Repository
public interface DictCountryRepository
        extends CrudRepository<DictCountry, DictCountryExample, Integer> {
    /**
     * @param locale
     * @return
     */
    List<DictCountry> selectCountriesByLocale(@Param("locale") String locale);

    /**
     * 获取所有国家主要字段(id,abbr , name , area_code , status,country_code,locale,letter_code2)的值
     *
     * @return
     */
    List<DictCountry> selectCountriesWithMainField();
}