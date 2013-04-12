package esride.opendatabridge.reader.csw;

import org.stringtemplate.v4.ST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: sma
 * Date: 09.04.13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class GetRecordsRequestGenerator {

    private ST templateObj;

    public void setGetRecordsTemplate(InputStream getRecordsTemplate)  {
        BufferedReader br = new BufferedReader(new InputStreamReader(getRecordsTemplate));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            templateObj = new ST(sb.toString(), '$', '$');
        }catch(IOException e){
            templateObj = null;
        }

    }

    public String generateGetRecordsTemplate(HashMap<String, String> template){
        Set<String> templateKeys = template.keySet();
        Iterator<String> iter =  templateKeys.iterator();
        while(iter.hasNext()){
            String key = iter.next();
            templateObj.add(key, template.get(key));
        }

        return templateObj.render();
    }
}
