package com.idb.controller;

import com.idb.model.NoticeBoard;
import com.idb.service.AppService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {

    @Autowired
    private AppService appService;

    // REFACTORING: Otomatis memasukkan 'nts' ke semua view di controller ini
    // Mengeliminasi duplikasi pemanggilan getAllNotice() di setiap metode.
    @ModelAttribute("nts")
    public List<NoticeBoard> populateNotices() {
        return appService.getAllNotice();
    }

    // REFACTORING: Menggabungkan metode duplikat yang memiliki logika identik
    @RequestMapping(value = {"/notice", "/noticeBoard", "/notice_board", "/notice-Board"}, method = RequestMethod.GET)
    public ModelAndView showNoticeView() {
        ModelAndView model = new ModelAndView("notice_view");
        model.addObject("notice", new NoticeBoard());
        return model;
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.GET)
    public ModelAndView showNoticeForUpdate(@RequestParam("n_id") Integer n_id) {
        ModelAndView model = new ModelAndView("update_notice");
        model.addObject("notice", appService.getNoticeById(n_id));
        return model;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public String addNotice(NoticeBoard noticeBoard) {
        appService.addNotice(noticeBoard);
        return "redirect:notice.htm";
    }

    @RequestMapping(value = "/update_notice", method = RequestMethod.POST)
    public String updateNotice(NoticeBoard noticeBoard) {
        appService.updateNptice(noticeBoard.getN_id(), noticeBoard.getN_title(), noticeBoard.getN_description());
        return "redirect:notice.htm";
    }
}