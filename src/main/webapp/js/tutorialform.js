/**
 * Created by tyler on 5/4/2017.
 */
/** This JavaScript file handles all of the tutorial form generation */

function addTextArea() {
    var count = getCount("texts");
    var total = getTotalCount();
    var id = getId("texts");

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h4>Text <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeById(\'' + id + '\');">Remove</button></h4><textarea name="texts[' + count + '].content" form="make-tutorial" rows="10" cols="150"></textarea><input type="hidden" name="texts[' + count + '].order" value="' + total + '"><br>';

    document.getElementById("tutorial").appendChild(div);
}

function addHeading() {
    var count = getCount("headings");
    var total = getTotalCount();
    var id = getId("headings");

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h4>Heading <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeById(\'' + id + '\');">Remove</button></h4><input type="text" name="headings[' + count + '].content"><input type="hidden" name="headings[' + count + '].order" value="' + total + '"><br>';

    document.getElementById("tutorial").appendChild(div);
}

function addImage() {
    var count = getCount("pictures");
    var total = getTotalCount();
    var id = getId("pictures");

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h4>Image Link <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeById(\'' + id + '\');">Remove</button></h4><input type="text" name="pictures[' + count + '].link" size="50"><input type="hidden" name="pictures[' + count + '].order" value="' + total + '"><br>';

    document.getElementById("tutorial").appendChild(div);
}

function addVideo() {
    var count = getCount("videos");
    var total = getTotalCount();
    var id = getId("videos");

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h4>Video Link <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeById(\'' + id + '\');">Remove</button></h4><input type="text" name="videos[' + count  + '].link" size="50"><input type="hidden" name="videos[' + count + '].order" value="' + total + '"><br>';
    document.getElementById("tutorial").appendChild(div);
}

function addCode() {
    var count = getCount("codes");
    var total = getTotalCount();
    var id = getId("codes");

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h4>Code <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeByIdProblems(\'' + id + '\');">Remove</button></h4>Description: <input type="text" name="codes[' + count + '].desc" size="100"><br><br><div class="editor" id="editor' + count + '"></div><div><textarea hidden class="codetext" name="codes[' + count + '].code" form="make-tutorial" rows="10" cols="150"></textarea><input type="hidden" name="codes[' + count + '].qid" value="' + total + '"></div><br>';
    div.innerHTML += '<h5>Test Cases <button class="btn-primary" id="addtest" onclick="event.preventDefault(); addTest(\'' + id + '\');">+</button></h5><div id="' + id + '-testcases"></div><input type="hidden" value="0" id="' + id + '-testCount">';

    document.getElementById("problems").appendChild(div);

    var editor = ace.edit('editor' + count);
    editor.setTheme("ace/theme/eclipse");
    var path = pickMode();
    editor.getSession().setMode(path);
}

function pickMode() {
    var base = "ace/mode/";
    if (language === "C") {
        base += "c_cpp";
    } else if (language === "C++") {
        base += "c_cpp";
    } else if (language === "Java 7") {
        base += "java";
    } else if (language === "Python 2") {
        base += "python";
    } else if (language === "Perl") {
        base += "perl";
    } else if (language === "PHP") {
        base += "php";
    } else if (language === "Ruby") {
        base += "ruby";
    } else if (language === "C#") {
        base += "csharp";
    } else if (language === "MySQL") {
        base += 'mysql';
    } else if (language === "Haskell") {
        base += 'haskell';
    } else if (language === "Clojure") {
        base += "clojure";
    } else if (language === "Scala") {
        base += "scala";
    } else if (language === "Erlang") {
        base += "erlang";
    } else if (language === "Lua") {
        base += "lua";
    } else if (language === "Javascript") {
        base += "javascript";
    } else if (language === "Go") {
        base += "golang";
    } else if (language === "D") {
        base += "d";
    } else if (language === "OCaml") {
        base += "ocaml";
    } else if (language === "R") {
        base += "r";
    } else if (language === "Pascal") {
        base += "pascal";
    } else if (language === "Common Lisp (SBCL)") {
        base += "lisp";
    } else if (language === "Python 3") {
        base += "python";
    } else if (language === "Groovy") {
        base += "groovy";
    } else if (language === "Objective-C") {
        base += "objectivec";
    } else if (language === "COBOL") {
        base += "cobol";
    } else if (language === "Tcl") {
        base += "tcl";
    } else if (language === "Java 8") {
        base += "java";
    } else if (language === "XQuery") {
        base += "xquery";
    } else if (language === "Rust") {
        base += "rust";
    } else if (language === "Swift") {
        base += "swift";
    } else if (language === "Fortran") {
        base += "fortran";
    }
    else {
        console.log("using plain text");
        base += "plain_text";
    }
    
    return base;
}

function loadEditors(count) {
    for(var i = 1; i < count; i++) {
        var editor = ace.edit('editor' + i);
        editor.setTheme("ace/theme/eclipse");
        var path = pickMode();
        editor.getSession().setMode(path);
    }

    var index = 1;

    $('textarea').each(function(){
        var name = $(this).attr('name');

        if(typeof name !== "undefined") {
            var editor = ace.edit('editor' + index);
            console.log($(this).val());
            editor.getSession().setValue($(this).val());
            index++;
        }
    });
}

function addTest(base_id) {
    var count = getCount(base_id + "-test");
    var total = getTotalCount();
    var id = getId(base_id + "-test");

    var codesCount = base_id.substring(5);

    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.innerHTML = '<h5><button class="btn-primary" id="removetest" onclick="event.preventDefault(); removeByIdTestcase(\'' + base_id + '\',\'' + id + '\');">x</button> Input: <textarea style="display: inline-block;vertical-align:middle;" id="bye" class="codetext" name="codes[' + codesCount + '].inputs[' + count + ']" form="make-tutorial" rows="3" cols="50"></textarea>  Answer: <textarea id="bye" class="codetext" style="display: inline-block;vertical-align:middle;" name="codes[' + codesCount + '].outputs[' + count + ']" form="make-tutorial" rows="3" cols="50"></textarea></h5>';

    document.getElementById(base_id + "-testcases").appendChild(div);
}

function addMC() {
    var num_ans;
    var msg = "How many answers would you like?";
    var answers;

    while(true) {
        num_ans = prompt(msg, 4);
        answers = parseInt(num_ans);

        if (num_ans == null) {
            break;
        }

        if(answers <= 1) {
            msg = "How many answers would you like?\nHEY! You need at least two answers.";
        } else if (answers < 9) {
            break;
        } else {
            msg = "How many answers would you like?\nHEY! Let's be reasonable.";
        }
    }

    if (num_ans != null) {
        var count = getCount("mcs");
        var total = getTotalCount();
        var id = getId("mcs");

        var div = document.createElement("div");
        div.setAttribute("id", id);
        div.innerHTML = '<h4>Multiple Choice <button class="btn-primary" id="remove" onclick="event.preventDefault(); removeByIdProblems(\'' + id + '\');">Remove</button></h4>Question: <input type="text" name="mcs[' + count + '].question" size="100"><input type="hidden" name="mcs[' + count + '].qid" value="' + total + '"><br>';

        var letter = 97;

        for (var i = 0; i < answers; i++) {
            div.innerHTML += '<div style="padding-left:5em">' + String.fromCharCode(letter) + ') <input type="text" name="mcs[' + count + '].choices[' + i + ']" size="50"><br></div>';
            letter++;
        }

        var answer = 'Answer: <select name="mcs[' + count + '].answer">';

        letter = 97;
        for (var i = 0; i < answers; i++) {
            answer += '<option value="' + i + '">' + String.fromCharCode(letter) + '</option>';
            letter++;
        }
        answer += '</select>';

        div.innerHTML += answer;

        document.getElementById("problems").appendChild(div);
    }
}

function submit() {
    $('textarea').each(function(){
        var name = $(this).attr('name');
        var bye = $(this).attr('id');

        if(typeof name !== "undefined" && bye !== "bye") {
            var editor = ace.edit('editor' + name.substring(6).split("]")[0]);
            $(this).val(editor.getSession().getValue());
        }
    });
    document.getElementById("make-tutorial").submit();
}

function getId(type) {
    var name = type + "Count";

    var count = document.getElementById(name).value;
    var id = type + document.getElementById(name).value;
    document.getElementById(name).value = parseInt(count)+1;

    var totalCount = document.getElementById("count").value;
    document.getElementById("count").value = parseInt(totalCount)+1;
    return id;
}

function getCount(type) {
    var name = type + "Count";

    var count = document.getElementById(name).value;
    return count;
}

function getTotalCount() {
    var count = document.getElementById("count").value;
    return count;
}

function removeById(id) {
    document.getElementById("tutorial").removeChild(document.getElementById(id));
}

function removeByIdProblems(id) {
    document.getElementById("problems").removeChild(document.getElementById(id));
}

function removeByIdTestcase(base_id, id) {
    document.getElementById(base_id + "-testcases").removeChild(document.getElementById(id));
}

function preview() {
    document.getElementById("make-tutorial").action = "/preview";
    document.getElementById("make-tutorial").target = "_blank";
    document.getElementById("make-tutorial").submit();
}

function problems() {
    document.getElementById("make-tutorial").action = "/createproblems";
    document.getElementById("make-tutorial").target = "_self";
    document.getElementById("make-tutorial").submit();
}

function answers() {
    document.getElementById("check-ans").action = "/check-ans";
    document.getElementById("check-ans").target = "_self";
    document.getElementById("check-ans").submit();
}

function runHackerRank(id, qid) {
    var index = 1;
    $('textarea').each(function(){
        var name = $(this).attr('name');

        if(typeof name !== "undefined") {
            var editor = ace.edit('editor' + index);
            $(this).val(editor.getSession().getValue());
            console.log($(this).val());
            index++;
        }
    });

    var lang = document.getElementById("language").value;

    var code = document.getElementById(id).value;
    var num = id.substring(1);
    var resultid = "result" + num;

    var json = {};
    json["language"] = lang;
    json["code"] = code;
    json["tutID"] = tutID;
    json["qid"] = qid;

    $.ajax({
        url: "/run",
        type: "POST",
        data: JSON.stringify(json),
        dataType: "JSON",
        success: function(reply) {
            var results = reply.results;
            var status = reply.status;

            for(var i = 0; i < results.length; i++) {
                var tempId = id + "-output" + (i+1);
                var output = document.getElementById(tempId);
                var div = document.getElementById(tempId + "-div");

                output.innerHTML = results[i];

                if(status[i] === 0) {
                    div.style.backgroundColor = '#ff8080';
                    $('input#' + tempId).val(0);
                }
                else {
                    div.style.backgroundColor = '#3dffa4';
                    $('input#' + tempId).val(1);
                }
            }
        }
    });
}