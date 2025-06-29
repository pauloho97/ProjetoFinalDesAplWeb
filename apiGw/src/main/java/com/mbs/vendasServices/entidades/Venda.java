package com.mbs.vendasServices.entidades;

import java.util.Date;

public class Venda {

    private Integer numeroVenda;     // ID da venda
    private String nomeProduto;      // nome da obra
    private Double precoProduto;
    private Integer quantidade;
    private String celular;
    private String artista;
    private String material;
    private String tipoObra;
    private Date dataVenda;
    private String cpf;
    private String status;
     

    // Getters e Setters
   
    
    
    public void setNumeroVenda(Integer numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

   
    public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Integer getNumeroVenda() {
		return numeroVenda;
	}


	public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}
