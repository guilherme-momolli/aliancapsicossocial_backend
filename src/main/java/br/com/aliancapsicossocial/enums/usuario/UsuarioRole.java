package br.com.aliancapsicossocial.enums.usuario;

public enum UsuarioRole {
    ADMIN("admin"),
    PROFISSIONAL("profissional"),
    PACIENTE("paciente"),
    EMPRESA("empresa"),
    SUPORTE("suporte"),
    ANALISTA("analista");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
