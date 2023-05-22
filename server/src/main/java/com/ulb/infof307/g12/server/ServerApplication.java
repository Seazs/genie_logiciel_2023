package com.ulb.infof307.g12.server;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ServerApplication {
	@Getter
	private static String storageFolderPath = "/server/src/main/resources/stockage/";
	@Getter
	private static String rootPath;
	public static void main(String[] args) {
		// Ce code permet de prendre le chemin du dossier root de l'application
		// Prendre la localisation de la classe Main → qui sera dans root/target/classe
		// Après, il faut revenir deux fichiers en arrière.
		String mainClassFolderTarget = ServerApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File tmp = new File(mainClassFolderTarget).getParentFile().getParentFile();
		rootPath = tmp.getPath();

		SpringApplication.run(ServerApplication.class, args);
	}

	public static String getStockageFolderPath(){
		return rootPath + storageFolderPath;
	}
}
