function menuSwitch(url){

    var menuElement = document.getElementById('menus');
    var lisElement = menuElement.getElementsByTagName("li");

    for(i=0;i<lisElement.length;i++){
        var aElement = lisElement[i].getElementsByTagName("a")[0];
        aElement.className = "";
    }

    this.getElementsByTagName("a")[0].className = "active";

    document.getElementById("iframe_content").src = url;
}