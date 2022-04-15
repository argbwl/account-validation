package com.ab.poller;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.DirectoryScanner;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.RecursiveDirectoryScanner;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;

import com.ab.logger.AVLogger;

@Configuration
public class FileReaderIntergrationPoller {
	
	private static final AVLogger logger = AVLogger.getLogger(FileReaderIntergrationPoller.class);
	
	@Value("${filePollerReaderDir}")
	private String pollerTxtFileDir;
	
	@Value("${filePollerWriterDir}")
	private String pollerTxtFileWriterDir;
	
	@Autowired
	private Transformer transformer;
	
	@Bean
	@InboundChannelAdapter(value = "pollerTxtFile",poller = @Poller (fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource() {
		FileReadingMessageSource sourceReader = new FileReadingMessageSource();
		sourceReader.setScanner(directoryScanner("([^\\s]+(\\.(?i)(txt))$)"));
		logger.info("Start checking file in {}",pollerTxtFileDir);
		System.out.println("FileReaderIntergrationPoller.fileReadingMessageSource()");
		sourceReader.setDirectory(new File(pollerTxtFileDir));
		return sourceReader;
	}
	
	@Bean
	@ServiceActivator(inputChannel = "pollerTxtFile")
	public FileWritingMessageHandler fileWritingMessageHandler() {
		FileWritingMessageHandler writerHandler = new FileWritingMessageHandler(new File(pollerTxtFileWriterDir));
		writerHandler.setAutoCreateDirectory(true);
		writerHandler.setExpectReply(false);
		writerHandler.setDeleteSourceFiles(true);
		//writerHandler.setRequiresReply(true);
		return writerHandler;
	}
	
	@Bean
    public DirectoryScanner directoryScanner(String regex) {
        DirectoryScanner scanner = new RecursiveDirectoryScanner();
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>(
                Arrays.asList(new AcceptOnceFileListFilter<>(),
                        new RegexPatternFileListFilter(regex))
        );
        scanner.setFilter(filter);
        return scanner;
    }
	
	@Bean
    public IntegrationFlow processFileFlow() {
        return IntegrationFlows
                .from(fileReadingMessageSource())
                .transform(transformer,"transform")
                .handle(fileWritingMessageHandler())
                .get();
    }
	
}
