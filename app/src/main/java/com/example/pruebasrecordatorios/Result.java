package com.example.pruebasrecordatorios;

import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
        @SerializedName("result")
        private List<ResponsePeticion> result = null;

        public List<ResponsePeticion> getResult() {
            return result;
        }

        public void setResult(List<ResponsePeticion> result) {
            this.result = result;
        }

}
