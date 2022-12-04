package com.example.springSecurityApplication.services;

import com.example.springSecurityApplication.models.Manuscript;
import com.example.springSecurityApplication.repositories.ManuscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManuscriptService {
    private final ManuscriptRepository manuscriptRepository;

    @Autowired
    public ManuscriptService(ManuscriptRepository manuscriptRepository) {
        this.manuscriptRepository = manuscriptRepository;
    }

    //Данный метод позволяет вернуть все рукописи
    public List<Manuscript> getAllManuscript() {
        return manuscriptRepository.findAll();
    }

    //Данный метод позволяет вернуть рукопись по id
    public Manuscript getManuscriptId(int id) {
        Optional<Manuscript> optionalManuscript = manuscriptRepository.findById(id);
        return optionalManuscript.orElse(null);
    }

    //Данный метод позволяет сохранить рукопись
    @Transactional
    public void saveManuscript(Manuscript manuscript) {
        manuscriptRepository.save(manuscript);
    }

    //Данный метод позволяет обновить данные рукописи
    @Transactional
    public void updateManuscript(int id, Manuscript manuscript) {
        manuscript.setId(id);
        manuscriptRepository.save(manuscript);
    }

    //Данный метод позволяет удалить рукопись по id
    @Transactional
    public void deleteManuscript(int id) {
        manuscriptRepository.deleteById(id);
    }
}
