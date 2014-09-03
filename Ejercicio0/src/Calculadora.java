
public class Calculadora implements ICalculadora {

	@Override
	public float sumar(float a, float b) {
		return a + b;
	}

	@Override
	public float restar(float a, float b) {
		return a - b;
	}

	@Override
	public float multiplicar(float a, float b) {
		return a * b;
	}

	@Override
	public float dividir(float a, float b) {
		return a / b;
	}
}
