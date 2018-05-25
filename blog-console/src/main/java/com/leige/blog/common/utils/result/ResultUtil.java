package com.leige.blog.common.utils.result;


import com.leige.blog.common.enums.ResultEnum;

/**
 * Created by leige.
 */
public class ResultUtil {
    public static Result success(ResultEnum resultEnum, Object data){
        Result result=new Result();
        result.setState(true);
        result.setData(data);
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return  result;
    }
    public static Result fail(ResultEnum resultEnum){
        Result result=new Result();
        result.setState(false);
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return  result;
    }

}
