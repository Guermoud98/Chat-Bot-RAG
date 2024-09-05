package com.stellantis.chatbot_kpi.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class RagDataLoader {

    @Value("classpath:/pdfs/cv.pdf")
    private Resource pdfResource; //contient le chemin +fournit des méthode pour lire et traiter le fichier
    @Value("store-data-v1.json")
    private String storeFile; //pour stocker les données vectorielless

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);
        String fileStore = Path.of("src","main","resources","store")
                .toAbsolutePath()+"/"+storeFile; //création du chemin absolu ou ls data vectorielles seront stokées ou chargées
        File file = new File(fileStore);
        // verification si le fichier de stockage existe
        if(!file.exists()) {
            PagePdfDocumentReader pdfDocumentReader = new PagePdfDocumentReader(pdfResource);
            List<Document> documents = pdfDocumentReader.get(); // lecture du fichier pdf + sa conversion en une liste Documents
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.split(documents);
            vectorStore.accept(chunks);
            vectorStore.save(file);
            System.out.println("saved");

        } else {
            vectorStore.load(file);
        }
        return vectorStore;
    }
}
