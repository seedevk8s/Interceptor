package com.earth.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 싱글톤(하나의 객체)이라서 여러 쓰레드가 하나의 객체를 공유함
@Component
public class PerformanceInterceptor implements HandlerInterceptor { // 단일 책임의 원칙(SRP) - 하나의 메서드는 하나의 책임만 가짐

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 전처리 작업
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);       //request객체에 startTime을 저장함

        // Object handler - 요청과 연결된 컨트롤러 메서드 가리킴
        HandlerMethod method = (HandlerMethod) handler;
        System.out.println("method.getMethod() =  " + method.getMethod());  // URL과 연결된 메서드
        System.out.println("method.getBean() = " + method.getBean());       // 메서드가 포함된 컨트롤러임

        // 리턴이 true면 다음 인터셉터나 컨트롤러를 호출함
        // 리턴이 false면 호출안함
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 2. 후처리 작업
        long startTime = (long) request.getAttribute("startTime");
        System.out.println("[" +request.getRequestURI()+ "]");
        System.out.println(" 소요시간 = " + (System.currentTimeMillis()- startTime) +"ms");


        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
