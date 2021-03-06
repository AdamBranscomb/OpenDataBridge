package esride.opendatabridge.application;

import esride.opendatabridge.agolreader.IAgolItemReader;
import esride.opendatabridge.agolwriter.IAgolService;
import esride.opendatabridge.pipeline.controller.PipelineController;
import esride.opendatabridge.processinfo.IProcessInfo;
import esride.opendatabridge.reader.IReader;
import esride.opendatabridge.reader.ReaderException;
import esride.opendatabridge.reader.factory.CatalogReaderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

/**
 * The AppInitializer manages the main components ({@link esride.opendatabridge.agolwriter.AgolService AgolService} and Catalog Adapter
 * via {@link esride.opendatabridge.reader.factory.CatalogReaderFactory CatalogReaderFactory}) via Spring configuration
 * User: sma
 * Date: 03.05.13
 * Time: 14:50
 */
public class AppInitializer {
    
    private IReader reader;

    private IAgolService agolService;

    private IAgolItemReader agolItemReader;

    private PipelineController pipelineController;


    public AppInitializer(StartParameter startParam) throws ReaderException {

        //Spring initialisieren
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"appconfig/transformerConfig.xml"});

        //Auslesen der Process Informationen
        IProcessInfo processInfo = context.getBean(startParam.getReaderValue() + "ProcessInfo", IProcessInfo.class);
        HashMap<String, String> properties = processInfo.getProperties(startParam.getPidValue());

        //ReaderFactory initialisieren
        CatalogReaderFactory factory = context.getBean("readerfactory", CatalogReaderFactory.class);        
        reader = factory.newReaderInstance(startParam.getReaderValue(), properties, startParam.getPidValue());

        //AgolService auslesen
        agolService = context.getBean("agolservice", IAgolService.class);

        //PipelineController auslesen
        pipelineController = context.getBean("pipelinecontroller", PipelineController.class);

        agolItemReader = context.getBean("agolReader", IAgolItemReader.class);
    }

    public IReader getReader() {
        return reader;
    }

    public IAgolService getAgolService(){
        return agolService;
    }

    public PipelineController getPipelineController() {
        return pipelineController;
    }

    public IAgolItemReader getAgolItemReader() {
        return agolItemReader;
    }
}
