package com.jpz.mynews.Controllers.Utils;

import com.jpz.mynews.Models.Doc;
import com.jpz.mynews.Models.Result;

import java.util.List;

public interface GetList {

    void receivedArticles(List<Result> resultList, List<Doc> docList);

}
