package com.mbs.vendasServices.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mbs.vendasServices.entidades.Venda;

@Repository
public class VendasRepositorio {

    private static Integer idContador = 0;
    private final Map<Integer, Venda> vendasPorId = new HashMap<>();

    public Integer salvar(Venda venda) {
        idContador++;
        venda.setNumeroVenda(idContador);
        vendasPorId.put(idContador, venda);
        return idContador;
    }

    public List<Venda> listar() {
        return new ArrayList<>(vendasPorId.values());
    }
}
