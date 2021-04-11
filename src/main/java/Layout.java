import com.github.manliogit.javatags.element.Element;

import static com.github.manliogit.javatags.lang.HtmlHelper.*;

public class Layout {

    private final String _title;
    private final Element _bodyContent;

    public Layout(String title, Element bodyContent) {
        _title = title;
        _bodyContent = bodyContent;
    }

    public Element build() {
        return
                html5(
                        head(
                                meta(attr("charset -> utf-8")),
                                meta(attr("http-equiv -> X-UA-Compatible", "content -> IE=edge")),
                                meta(attr("name -> viewport", "content -> width=device-width, initial-scale=1")),
                                title(_title),
                                style(
                                        text("table {border: 1px solid black;border-collapse: collapse;}.deleted {background-color: lightgray;}.changed {background-color: lightblue;}.added {background-color: lightgreen ;}")
                                ),
                                link(attr("rel -> stylesheet", "href -> /css/bootstrap.min.css")),
                                link(attr("rel -> stylesheet", "href -> /css/app.css"))
                        ),
                        body(
                                _bodyContent,
                                script(attr("src -> /js/jquery.min.js")),
                                script(attr("src -> /js/bootstrap.min.js"))
                        )
                );
    }

}
