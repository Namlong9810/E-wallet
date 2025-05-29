package e_wallet.transaction_service.utils;

import java.util.Random;

public class IpGenerator {
    private static final Random random = new Random();

    public static String generateRandomIp() {
        return random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256);
    }
}