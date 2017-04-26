function set_size(obj)
{
    var strs=obj.firstChild.nodeValue.split(/\r|\n/);
    var lines=strs.length; // получаем количество строк, оканчивающихся переводом строки
    /*  в коде ниже мы пытаемся понять, какие строки длиннее чем наша textarea, и, соотвественно, учитываем что перенос переходит только по словам*/
    for (var i=0;i<strs.length;i++)
    {
        var words=strs[i].split(' ');
        for (var k=j=0;j<words.length;j++)
        {
            k+=words[j].length+1;
            if (k>obj.cols)
            {
                k=0;
                lines++;
            }
        }
    }
    obj.rows=lines<20 ? (lines>10 ? lines : 10) : 20;
}