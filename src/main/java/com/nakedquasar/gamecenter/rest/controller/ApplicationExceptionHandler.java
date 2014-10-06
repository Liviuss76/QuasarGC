package com.nakedquasar.gamecenter.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nakedquasar.gamecenter.rest.controller.beans.RestResponse;
import com.nakedquasar.gamecenter.rest.controller.errors.ErrorInfo;

@Controller
@RequestMapping("/errors")
public class ApplicationExceptionHandler {

    @RequestMapping("/resourcenotfound")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestResponse resourceNotFound(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return new RestResponse(null, new ErrorInfo("Resource Not Found"));
    }

    @RequestMapping("/unauthorised")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestResponse unAuthorised(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return new RestResponse(null, new ErrorInfo("Unauthorised Request"));

    }
}
