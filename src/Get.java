import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.Enumeration;
import java.time.LocalDateTime;

public class Get {
    public static void checkGet(String subcommand, String value) {
        switch (subcommand) {
            case "date":
                System.out.println("The current date is: " + getDate());
                break;
            case "ip":
                getIp();
                break;
            default:
                System.out.println("Sorry, but I don't know the command " + subcommand);
                break;
        }
    }

    private static String getDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.toString();
    }

    private static void getIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface net = interfaces.nextElement();

                if (!net.isUp() || net.isLoopback() || net.isVirtual()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = net.getInetAddresses();

                if (!addresses.hasMoreElements()) {
                    continue;
                }

                String description = "";

                if (net.getName().equalsIgnoreCase("ethernet_32769")) {
                    description = "(Local Network / LAN)";
                } else if (net.getName().equalsIgnoreCase("ethernet_32774")) {
                    description = "(Virtual Adapter e.g. VirtualBox)";
                } else if (net.getName().equalsIgnoreCase("iftype53_32770")) {
                    description = "(Carrier NAT / ISP or VPN)";
                }

                System.out.println("Interface: " + net.getName() + " " + description);

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    String type;

                    if (addr.isLoopbackAddress()) {
                        type = "Loopback";

                    } else if (addr.isLinkLocalAddress()) {
                        type = "Link-Local";

                    } else if (addr instanceof Inet4Address && addr.isSiteLocalAddress()) {
                        if (net.getName().equalsIgnoreCase("iftype53_32770")) {
                            type = "VPN/Private";
                        } else {
                            type = "Local/LAN";
                        }

                    } else if (addr instanceof Inet4Address) {
                        byte[] bytes = addr.getAddress();

                        int first = bytes[0] & 255;
                        int second = bytes[1] & 255;

                        if (first == 100 && second >= 64 && second <= 127) {
                            type = "CGNAT";
                        } else {
                            type = "Public IPv4";
                        }

                    } else {
                        type = "IPv6";
                    }

                    String version;

                    if (addr instanceof Inet4Address) {
                        version = "IPv4";
                    } else {
                        version = "IPv6";
                    }

                    System.out.println("  " + version + ": " + addr.getHostAddress() + " (" + type + ")");
                }

                System.out.println();
            }

            URI uri = URI.create("https://api.ipify.org");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream()))) {
                String ip = reader.readLine();

                System.out.println("Public IP: " + ip + " (Can change with VPN)");
            }

        } catch (Exception e) {
            System.out.println("Could not find network information.");
        }
    }
}
