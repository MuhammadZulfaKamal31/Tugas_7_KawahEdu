package org.acme.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.acme.Model.Kebun;

@ApplicationScoped
public class PanenService {

    // get
    public List<Kebun> getKebun() {
        List<Kebun> listKomoditas = Kebun.listAll();
        return listKomoditas;
    }

    // get uuid
    public Kebun getKomoditasByUUID(UUID id) {
        return Kebun.findById(id);
    }

    // post uuid
    @Transactional
    public List<Kebun> createKebun(Kebun komoditas) {
        komoditas.id = UUID.randomUUID();
        komoditas.createAt = LocalDate.now();
        komoditas.updateAt = LocalDate.now();
        komoditas.persist();
        return Kebun.listAll();
    }

    // update
    @Transactional
    public Kebun updateNamaKebun(UUID id, Kebun komoditas) {
        Kebun entity = Kebun.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.namaBarang = komoditas.namaBarang;

        return entity;
    }

    // update
    @Transactional
    public Kebun addTotal(UUID id, Kebun komoditas) {
        Kebun entity = Kebun.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.total = komoditas.total + entity.total;
        entity.updateAt = LocalDate.now();

        return entity;
    }

    // delete
    @Transactional
    public List<Kebun> deleteKebun(UUID id) {
        Kebun entity = Kebun.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
        return Kebun.listAll();
    }
}

// PENULISAN POST DI POSTMAN

// {
// "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
// "namaBarang": "string",
// "total": 0,
// "createAt": "2022-03-10",
// "updateAt": "2022-03-10"
// }
