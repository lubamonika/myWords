package servlets;

import com.infoshareacademy.emememsy.Actions;
import com.infoshareacademy.emememsy.InputOutput;
import com.infoshareacademy.emememsy.PropertiesReader;
import com.infoshareacademy.emememsy.SingleWord;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/choose-category")
public class ChooseCategoryServlet extends HttpServlet {

    @Inject
    private TemplateProvider templateProvider;
    private InputOutput inputOutput;
    private Actions actions;
    private PropertiesReader propertiesReader;
    private SingleWord singleWord;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mode = req.getParameter("mode");

        if (mode == null || mode.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Template template = templateProvider.getTemplate(getServletContext(), "choose-category.ftlh");

        List<SingleWord> tempList = inputOutput.createListOfWordsOmmitProperties();
        //tempList = InputOutput.createListOfWordsOmmitProperties();
        List<String> categories = tempList.stream()
                .map(o -> o.getCategory())
                .distinct()
                .collect(Collectors.toList());


        Map<String, Object> model = new HashMap<>();
        model.put("mode", mode);
        model.put("category", categories);

        resp.setContentType("text/html;charset=UTF-8");

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}






