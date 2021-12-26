package com.yjg.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @author yujiangong
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean1 {
    private Integer code;
    private Object obj;

    /**
     * 成功返回结果
     *
     * @return
     */
    public static RespBean1 success() {
        return new RespBean1(200, null);
    }


    /**
     * 成功返回结果
     *
     * @param obj
     * @return
     */
    public static RespBean1 success( Object obj) {
        return new RespBean1(200, obj);
    }

    /**
     * 失败返回结果
     *
     * @return
     */
    public static RespBean1 error() {
        return new RespBean1(500, null);
    }

    /**
     * 失败返回结果
     *
     * @param obj
     * @return
     */
    public static RespBean1 error( Object obj) {
        return new RespBean1(500, obj);
    }
}
