package com.solusoft.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service 
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIRECTORIO_UPLOAD= "uploads";
	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		
		Path rutaArchivo = getPath(nombreFoto);
		log.info(rutaArchivo.toString());//implementando el log
		
		Resource recurso = new UrlResource(rutaArchivo.toUri());//convertir la ruta a una URL
		
		
		//si el recurso existe y sea accesible
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());//convertir la ruta a una URL
			
			log.error("No se pudo cargar la imagen "+nombreFoto);
		}
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		//copiar el archivo que se ha subido al servidor a la ruta escogida
		String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(nombreArchivo);
		log.info(rutaArchivo.toString());//implementado el log
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
	
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		if(nombreFoto != null && nombreFoto.length() > 0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();//convertir a un objeto de tipo file
			
			//Si el archivo existe
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {//Si existe en el directorio
				archivoFotoAnterior.delete();//se elimina
				return true;
			}
		}
			
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {//Construye el path y lo retorna
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
