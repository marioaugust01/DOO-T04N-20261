package objects;

public class WeatherPrinter {

    public static void exibir(WeatherResponse response) {
        if (!response.isSucesso()) {
            System.out.println("Problema ao obter os dados: " + response.getBody());
            return;
        }

        CurrentConditions atual = response.getCurrentConditions();
        WeatherDay hoje = response.getDays().isEmpty() ? null : response.getDays().get(0);

        System.out.println("============================================");
        System.out.println("  Clima em: " + response.getResolvedAddress());
        System.out.println("  Fuso horário: " + response.getTimezone());
        System.out.println("============================================");

        if (atual != null) {
            System.out.println("Hora da consulta: " + atual.getDatetime());
            System.out.println("Temperatura atual: " + formatarNumero(atual.getTemp()) + " °C");
            System.out.println("Umidade: " + formatarNumero(atual.getHumidity()) + " %");
            System.out.println("Condição: " + atual.getConditions());
            System.out.println("Precipitação: " + formatarPrecipitacao(atual.getPrecip()));
            System.out.println("Vento: " + formatarNumero(atual.getWindspeed()) + " km/h — " + formatarDirecao(atual.getWinddir()));
        }

        if (hoje != null) {
            System.out.println("--------------------------------------------");
            System.out.println("Máxima do dia: " + formatarNumero(hoje.getTempmax()) + " °C");
            System.out.println("Mínima do dia: " + formatarNumero(hoje.getTempmin()) + " °C");
        }

        System.out.println("============================================");
    }

    private static String formatarNumero(Double valor) {
        if (valor == null) return "N/A";
        return String.format("%.1f", valor);
    }

    private static String formatarPrecipitacao(Double precip) {
        if (precip == null || precip == 0.0) return "Sem precipitação";
        return formatarNumero(precip) + " mm";
    }

    private static String formatarDirecao(Double graus) {
        if (graus == null) return "N/A";

        String[] pontos = {"Norte", "Nordeste", "Leste", "Sudeste", "Sul", "Sudoeste", "Oeste", "Noroeste"};
        int indice = (int) Math.round(graus / 45.0) % 8;
        return pontos[indice];
    }
}
