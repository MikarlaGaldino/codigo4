import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class LocacaoCarro {
    private String modelo;
    private Date inicio;
    private Date fim;
    private double valorPorHora;
    private double valorDiario;

    public LocacaoCarro(String modelo, Date inicio, Date fim, double valorPorHora, double valorDiario) {
        this.modelo = modelo;
        this.inicio = inicio;
        this.fim = fim;
        this.valorPorHora = valorPorHora;
        this.valorDiario = valorDiario;
    }

    public long getDuracaoLocacaoEmHoras() {
        long diferencaEmMillies = Math.abs(fim.getTime() - inicio.getTime());
        return TimeUnit.HOURS.convert(diferencaEmMillies, TimeUnit.MILLISECONDS);
    }

    public double calcularValorLocacao() {
        long horas = getDuracaoLocacaoEmHoras();
        if (horas <= 12) {
            return horas * valorPorHora;
        } else {
            long dias = horas / 24;
            long horasRestantes = horas % 24;
            double locacaoDiaria = dias * valorDiario;
            double locacaoPorHora = horasRestantes * valorPorHora;
            return locacaoDiaria + locacaoPorHora;
        }
    }

    public double calcularImposto(double valorLocacao) {
        if (valorLocacao <= 100.0) {
            return valorLocacao * 0.20;
        } else {
            return valorLocacao * 0.15;
        }
    }

    public void imprimirNota() {
        double valorLocacao = calcularValorLocacao();
        double imposto = calcularImposto(valorLocacao);
        double pagamentoTotal = valorLocacao + imposto;

        System.out.println("NOTA DE PAGAMENTO:");
        System.out.println("Modelo do carro: " + modelo);
        System.out.println("Duração da locação (horas): " + getDuracaoLocacaoEmHoras());
        System.out.println("Valor da locação: R$ " + String.format("%.2f", valorLocacao));
        System.out.println("Imposto: R$ " + String.format("%.2f", imposto));
        System.out.println("Pagamento total: R$ " + String.format("%.2f", pagamentoTotal));
    }
}

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.print("Modelo do carro: ");
        String modelo = sc.nextLine();

        System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
        Date inicio = sdf.parse(sc.nextLine());

        System.out.print("Data e hora de fim (dd/MM/yyyy HH:mm): ");
        Date fim = sdf.parse(sc.nextLine());

        System.out.print("Valor por hora: ");
        double valorPorHora = sc.nextDouble();

        System.out.print("Valor diário: ");
        double valorDiario = sc.nextDouble();

        LocacaoCarro locacao = new LocacaoCarro(modelo, inicio, fim, valorPorHora, valorDiario);
        locacao.imprimirNota();

        sc.close();
}
}
