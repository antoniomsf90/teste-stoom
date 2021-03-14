package br.com.stoom.ms.adress.google.geocoding.dto;

import java.util.List;

public class Results
{
    private List<Address_components> address_components;

    private String formatted_address;

    private Geometry geometry;

    private String place_id;

    private List<String> types;

    public void setAddress_components(List<Address_components> address_components){
        this.address_components = address_components;
    }
    public List<Address_components> getAddress_components(){
        return this.address_components;
    }
    public void setFormatted_address(String formatted_address){
        this.formatted_address = formatted_address;
    }
    public String getFormatted_address(){
        return this.formatted_address;
    }
    public void setGeometry(Geometry geometry){
        this.geometry = geometry;
    }
    public Geometry getGeometry(){
        return this.geometry;
    }
    public void setPlace_id(String place_id){
        this.place_id = place_id;
    }
    public String getPlace_id(){
        return this.place_id;
    }
    public void setTypes(List<String> types){
        this.types = types;
    }
    public List<String> getTypes(){
        return this.types;
    }
}