package com.yjg.blog.pojo;

import lombok.*;

/**
 * 公共返回对象
 *
 * @author yujiangong
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private Integer code;
    private String message;
    private Object obj;
    private Integer total;
    /**
     * 成功返回结果
     *
     * @param message
     * @return
     */
    public static RespBean success(String message,Integer total) {
        return new RespBean(200, message, null,total);
    }


    /**
     * 成功返回结果
     *
     * @param message
     * @param obj
     * @return
     */
    public static RespBean success(String message, Object obj,Integer total) {
        return new RespBean(200, message, obj,total);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @return
     */
    public static RespBean error(String message) {
        return new RespBean(500, message, null,0);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @param obj
     * @return
     */
    public static RespBean error(String message, Object obj) {
        return new RespBean(500, message, obj,0);
    }
}
