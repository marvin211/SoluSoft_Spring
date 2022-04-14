package com.solusoft.springboot.backend.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	public Resource cargar(String nombreFoto) throws MalformedURLException;//Mostrar la imagen
	public String copiar(MultipartFile archivo) throws IOException;//Copiar la imagen
	public boolean eliminar(String nombreFoto);//Eliminar la imagen
	public Path getPath(String nombreFoto);//Obtener la ruta
	
}
