package com.ejemplo.api.servicio;

import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.ImagenPortada;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.ImagenPortadaRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortadaServicioImpl implements ImagenPortadaServicio{

    private final InmuebleRepo inmuebleRepo;

    private final ImagenPortadaRepo imagenPortadaRepo;
    private final String CARPETA_PATH = "C:\\Users\\sbria\\OneDrive\\Escritorio\\imagenPortada\\";


    @Override
    public String subirImagenPortada(MultipartFile archivo, Integer id) throws IOException {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontro el inmueble"));


                String filePath = CARPETA_PATH + archivo.getOriginalFilename();

                ImagenPortada imagenPortada = new ImagenPortada();
                imagenPortada.setNombre(archivo.getOriginalFilename());
                imagenPortada.setTipo(archivo.getContentType());
                imagenPortada.setInmueble(inmueble);
                imagenPortada.setFilePath(filePath);

                imagenPortadaRepo.save(imagenPortada);
                try {
                    File file = new File(filePath);
                    archivo.transferTo(file);
                } catch (Exception e) {
                    return "Error al cargar la portada " + archivo.getOriginalFilename();
                }


            return "Imagenes subidas correctamente";
        }catch (Exception e){
            throw new RuntimeException("No se pudo encontrar el id " + id);
        }
    }


    @Override
    public List<Imagen> listaImagenPortada(int page, int size) {
        return null;
    }

    @Override
    public String eliminarIamgenPortada(Integer id) {
        return null;
    }

    @Override
    public void actualizar(Imagen imagen, Integer id) {

    }
}
