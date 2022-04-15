package com.ab.poller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.ab.logger.AVLogger;

@Component
public class Transformer {
	
	private static AVLogger logger = AVLogger.getLogger(Transformer.class);
	
	public String transform(String path) throws IOException {
		Path nioPath = Paths.get(path);
		String content = new String(Files.readAllBytes(nioPath));
		logger.info("content file {} is >> {}",nioPath.getFileName().toString(),content);
		return content;
	}

}
