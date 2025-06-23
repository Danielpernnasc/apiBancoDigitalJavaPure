package com.api.apiBookStore.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;

import java.io.IOException;

import com.api.apiBookStore.util.JwUtil;


public class TokenValidationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente ou inválido");
            return;
        }

        String token = authHeader.substring(7);
        if (!JwUtil.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write("{\"valid\": true}");
    }
}
