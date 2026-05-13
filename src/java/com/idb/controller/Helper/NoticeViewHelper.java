package com.idb.controller.helper;
 
import com.idb.model.NoticeBoard;
import com.idb.service.AppService;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;

public class NoticeViewHelper {
    
    private final AppService appService;
    
    public NoticeViewHelper(AppService appService) {
        this.appService = appService;
    }
    
    /**
     * Add notice list to model (extracted duplicate pattern)
     */
    public void addNoticeListToModel(ModelAndView model) {
        List<NoticeBoard> notices = appService.getAllNotice();
        model.addObject("nts", notices);
    }
    
    /**
     * Setup notice form view (extracted duplicate pattern)
     */
    public void setupNoticeFormView(ModelAndView model) {
        NoticeBoard noticeBoard = new NoticeBoard();
        List<NoticeBoard> notices = appService.getAllNotice();
        
        model.addObject("notice", noticeBoard);
        model.addObject("nts", notices);
    }
    
    /**
     * Setup notice update view
     */
    public void setupNoticeUpdateView(ModelAndView model, int noticeId) {
        List<NoticeBoard> notice = appService.getNoticeById(noticeId);
        List<NoticeBoard> notices = appService.getAllNotice();
        
        model.addObject("nts", notices);
        model.addObject("notice", notice);
    }
}