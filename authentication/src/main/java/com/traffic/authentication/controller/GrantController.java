package com.traffic.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("authorizationRequest")
public class GrantController {

//    @Autowired
//    private PlatformService platformService;

//    @RequestMapping("/oauth/confirm_access")
//    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
//        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
//        ModelAndView view = new ModelAndView();
//        view.setViewName("grant");
//        view.addObject("clientId", authorizationRequest.getClientId());
//        //PlatformDO platformDO = platformService.getById(Long.parseLong(authorizationRequest.getClientId()));platformDO.getNameCn()
//        view.addObject("clientName", "静态交通");
//        return view;
//    }

}