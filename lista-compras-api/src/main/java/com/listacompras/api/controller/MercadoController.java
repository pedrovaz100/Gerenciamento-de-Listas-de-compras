package com.listacompras.api.controller;

import com.listacompras.api.dto.MercadoRequestDTO;
import com.listacompras.api.dto.MercadoResponseDTO;
import com.listacompras.api.model.MercadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercados")
@RequiredArgsConstructor
public class MercadoController {

    private final MercadoService mercadoService;

    @GetMapping
    public Page<MercadoResponseDTO> listar(Pageable pageable) {
        return mercadoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public MercadoResponseDTO buscar(@PathVariable Long id) {
        return mercadoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoResponseDTO criar(@Valid @RequestBody MercadoRequestDTO mercado) {
        return mercadoService.criar(mercado);
    }

    @PutMapping("/{id}")
    public MercadoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody MercadoRequestDTO mercado) {
        return mercadoService.atualizar(id, mercado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        mercadoService.deletar(id);
    }
}
