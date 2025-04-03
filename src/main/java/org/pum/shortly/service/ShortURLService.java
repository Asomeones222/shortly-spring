package org.pum.shortly.service;

import org.pum.shortly.exception.CodeGenerationFailureException;
import org.pum.shortly.model.ShortURL;
import org.pum.shortly.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortURLService {
    private final ShortURLRepository shortURLRepository;
    private final SecureRandom secureRandom;

    @Autowired
    public ShortURLService(ShortURLRepository shortURLRepository, SecureRandom secureRandom) {
        this.shortURLRepository = shortURLRepository;
        this.secureRandom = secureRandom;
    }

    public String getMappedURL(String code) {
        return this.shortURLRepository.findByCode(code).map(ShortURL::getMappedURL).orElse(null);
    }

    public ShortURL createShortURL(String url) throws CodeGenerationFailureException {
        final int MAX_TRIES = 5;
        int numberOfTries = 1;
        while (numberOfTries <= MAX_TRIES) {
            var code = generateCode();
            if (this.shortURLRepository.existsByCode(code)) numberOfTries++;
            else {
                var shortURL = ShortURL.builder().code(code).mappedURL(url).build();
                this.shortURLRepository.save(shortURL);
                return shortURL;
            }
        }
        throw new CodeGenerationFailureException();
    }

    private String generateCode() {
        final int CODE_LENGTH = 7;
//        Based on the latest RFC standard section 2.3
        final String UNRESERVED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~";

        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = secureRandom.nextInt(UNRESERVED_CHARS.length());
            sb.append(UNRESERVED_CHARS.charAt(index));
        }
        return sb.toString();
    }
}
