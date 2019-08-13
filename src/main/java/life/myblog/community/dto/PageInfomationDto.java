package life.myblog.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hlq
 * @create 2019-08-11 21:44
 */
@Data
public class PageInfomationDto {
    private List<QuestionDto> questions;
    private boolean showPrevious;//是否有向前按钮
    private boolean showFirstPage;//是否有第一页按钮
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPageInfomation(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.currentPage = page;
        //page=1~4 list 1 2 3 4 5 6 7
        //page=5   list 2 3 4 5 6 7 8
        pages.add(page);
        for (int i = 0; i <= 3; i++) {
            if(page - i>0){
                if(page - i != page) {
                    pages.add(0, page - i);
                }
            }
            if (page + i <= totalPage){
                if(page + i != page){
                    pages.add(page+i);
                }
            }
        }

        //是否展示上一页
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示下一页
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
