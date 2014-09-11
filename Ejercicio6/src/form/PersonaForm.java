package form;

import java.util.Date;

public class PersonaForm {
	private Integer inputId;
	private String inputNombre;
	private String inputApellido;
	private Float inputAltura;
	private Date inputFecha;
	// Atributos de ordenador
	private String inputNombreOrdenador;
	private String inputSerialOrdenador;

	public Integer getInputId() {
		return inputId;
	}
	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}
	public String getInputNombre() {
		return inputNombre;
	}
	public void setInputNombre(String inputNombre) {
		this.inputNombre = inputNombre;
	}
	public String getInputApellido() {
		return inputApellido;
	}
	public void setInputApellido(String inputApellido) {
		this.inputApellido = inputApellido;
	}
	public Float getInputAltura() {
		return inputAltura;
	}
	public void setInputAltura(Float inputAltura) {
		this.inputAltura = inputAltura;
	}
	public Date getInputFecha() {
		return inputFecha;
	}
	public void setInputFecha(Date inputFecha) {
		this.inputFecha = inputFecha;
	}
	public String getInputNombreOrdenador() {
		return inputNombreOrdenador;
	}
	public void setInputNombreOrdenador(String inputNombreOrdenador) {
		this.inputNombreOrdenador = inputNombreOrdenador;
	}
	public String getInputSerialOrdenador() {
		return inputSerialOrdenador;
	}
	public void setInputSerialOrdenador(String inputSerialOrdenador) {
		this.inputSerialOrdenador = inputSerialOrdenador;
	}
}
