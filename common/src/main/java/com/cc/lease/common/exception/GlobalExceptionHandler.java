package com.cc.lease.common.exception;

import com.cc.lease.common.result.Result;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler({LeaseException.class})
    @ResponseBody
    public Result error(LeaseException e){
        e.printStackTrace();
        return Result.fail(e.getCode(),e.getMessage());
    }
}
