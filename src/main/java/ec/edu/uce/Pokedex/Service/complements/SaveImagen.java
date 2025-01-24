package ec.edu.uce.Pokedex.Service.complements;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;

@Service
public class SaveImagen {

    public void saveImageFromUrl (String imageUrl, String destinationPath) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Path outputPath = Paths.get(destinationPath);
            Files.createDirectories(outputPath.getParent()); // Crea los directorios si no existen
            Files.copy(in, outputPath);
        }catch (ExportException e){
            e.printStackTrace();
        }
    }
}
