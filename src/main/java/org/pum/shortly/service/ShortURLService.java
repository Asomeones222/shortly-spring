package org.pum.shortly.service;

import org.pum.shortly.exception.ShortURLGenerationFailureException;
import org.pum.shortly.model.ShortURL;
import org.pum.shortly.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class ShortURLService {
    private final ShortURLRepository shortURLRepository;
    private final SecureRandom secureRandom;

    @Autowired
    public ShortURLService(ShortURLRepository shortURLRepository,
                           SecureRandom secureRandom) {
        this.shortURLRepository = shortURLRepository;
        this.secureRandom = secureRandom;
    }

    public Optional<ShortURL> getShortURL(String code) {
        return this.shortURLRepository.findByCode(code);
    }

    public ShortURL createShortURL(String url) throws ShortURLGenerationFailureException {
        final int MAX_TRIES = 5;
        int numberOfTries = 1;
        while (numberOfTries <= MAX_TRIES) {
            var code = generateCode();
            if (this.shortURLRepository.existsByCode(code)) {
                numberOfTries++;
                continue;
            }
            var shortURL = ShortURL
                    .builder()
                    .code(code)
                    .mappedURL(url)
                    .build();
            this.shortURLRepository.save(shortURL);
            return shortURL;
        }
        throw new ShortURLGenerationFailureException();
    }

    private String generateCode() {
        final int MAX_CODE_LENGTH = 7;
        final int MIN_CODE_LENGTH = 4;
        final int CODE_LENGTH = secureRandom.nextInt(MIN_CODE_LENGTH, MAX_CODE_LENGTH + 1);
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
