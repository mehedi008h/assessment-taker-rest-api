package com.devmehedi.assessment.mapper;

import com.devmehedi.assessment.dto.ResultDTO;
import com.devmehedi.assessment.model.Result;
import org.springframework.beans.BeanUtils;

public class ResultMapper {
    public ResultDTO fromResult(Result result) {
        ResultDTO resultDTO = new ResultDTO();
        BeanUtils.copyProperties(result, resultDTO);
        resultDTO.setUser(result.getUser());
        return resultDTO;
    }

    public Result fromResultDTO(ResultDTO resultDTO) {
        Result result = new Result();
        BeanUtils.copyProperties(resultDTO, result);
        return result;
    }
}
