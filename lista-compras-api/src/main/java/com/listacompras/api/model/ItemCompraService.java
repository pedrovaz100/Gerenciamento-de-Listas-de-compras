package com.listacompras.api.model;

import com.listacompras.api.dto.ItemCompraRequestDTO;
import com.listacompras.api.dto.ItemCompraResponseDTO;
import com.listacompras.api.entity.ItemCompra;
import com.listacompras.api.entity.ListaCompra;
import com.listacompras.api.repository.ItemCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;
    private final ListaCompraService listaCompraService;

    public Page<ItemCompraResponseDTO> listar(Pageable pageable) {
        return itemCompraRepository.findAll(pageable).map(this::toResponse);
    }

    public ItemCompraResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public ItemCompraResponseDTO criar(ItemCompraRequestDTO request) {
        validarRegraDeNegocio(request);

        ListaCompra listaCompra = listaCompraService.buscarEntidadePorId(request.getListaCompraId());

        ItemCompra itemCompra = ItemCompra.builder()
                .nome(request.getNome())
                .quantidade(request.getQuantidade())
                .preco(request.getPreco())
                .listaCompra(listaCompra)
                .build();

        return toResponse(itemCompraRepository.save(itemCompra));
    }

    public ItemCompraResponseDTO atualizar(Long id, ItemCompraRequestDTO request) {
        validarRegraDeNegocio(request);

        ItemCompra itemCompra = buscarEntidadePorId(id);
        ListaCompra listaCompra = listaCompraService.buscarEntidadePorId(request.getListaCompraId());

        itemCompra.setNome(request.getNome());
        itemCompra.setQuantidade(request.getQuantidade());
        itemCompra.setPreco(request.getPreco());
        itemCompra.setListaCompra(listaCompra);

        return toResponse(itemCompraRepository.save(itemCompra));
    }

    public void deletar(Long id) {
        ItemCompra itemCompra = buscarEntidadePorId(id);
        itemCompraRepository.delete(itemCompra);
    }

    public Page<ItemCompraResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return itemCompraRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::toResponse);
    }

    public Page<ItemCompraResponseDTO> buscarPorFaixaPreco(Double precoMin, Double precoMax, Pageable pageable) {
        if (precoMin > precoMax) {
            throw new IllegalArgumentException("O preço mínimo não pode ser maior que o preço máximo");
        }

        return itemCompraRepository.findByPrecoBetween(precoMin, precoMax, pageable)
                .map(this::toResponse);
    }

    public Page<ItemCompraResponseDTO> buscarPorQuantidadeMinima(Integer quantidade, Pageable pageable) {
        return itemCompraRepository.findByQuantidadeGreaterThanEqual(quantidade, pageable)
                .map(this::toResponse);
    }

    private ItemCompra buscarEntidadePorId(Long id) {
        return itemCompraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de compra com id " + id + " não encontrado"));
    }

    private ItemCompraResponseDTO toResponse(ItemCompra itemCompra) {
        return ItemCompraResponseDTO.builder()
                .id(itemCompra.getId())
                .nome(itemCompra.getNome())
                .quantidade(itemCompra.getQuantidade())
                .preco(itemCompra.getPreco())
                .listaCompraId(itemCompra.getListaCompra().getId())
                .build();
    }

    private void validarRegraDeNegocio(ItemCompraRequestDTO request) {
        if (request.getPreco() > 10000) {
            throw new IllegalArgumentException("O preço do item não pode ser maior que R$ 10.000,00");
        }

        if (request.getQuantidade() > 1000) {
            throw new IllegalArgumentException("A quantidade do item não pode ser maior que 1000 unidades");
        }
    }
}
