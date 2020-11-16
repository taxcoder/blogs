package com.tanx.blogs.service.daily;

import com.tanx.blogs.pojo.model.Daily;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author tanxiang
 */
@Service
public interface DailyService {

    /**
     * 增加日记
     * @param daily 日记的信息
     * @return 返回增加结果
     */
    int addDaily(Daily daily);

    /**
     * 查询日记
     * @param paramInt 当前页
     * @return 返回指定页数的数据
     */
    List<Daily> queryDaily(int paramInt);

    /**
     * 查询日记的总数
     * @return 返回总数
     */
    int dailyCount();

    /**
     * 根据ID查询日记信息
     * @param paramLong 日记ID
     * @return 返回日记信息
     */
    Daily queryDailyById(String paramLong);

    /**
     * 增加日记的点赞数
     * @param paramInt 点赞数
     * @param paramLong 日记ID
     * @return 是否成功
     */
    int addDailyByGoodNumber(int paramInt, long paramLong);

    /**
     * 增加日记的评论数
     * @param paramInt 评论数
     * @param paramLong 日记ID
     * @return 是否成功
     */
    int addDailyByDiscussNumber(int paramInt, long paramLong);
}
