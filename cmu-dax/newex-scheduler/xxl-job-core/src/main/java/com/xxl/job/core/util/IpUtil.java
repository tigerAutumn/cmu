package com.xxl.job.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * get ip
 * @author xuxueli 2016-5-22 11:38:05
 */
public class IpUtil {
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	private static final String ANYHOST = "0.0.0.0";
	private static final String LOCALHOST = "127.0.0.1";
	public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	private static volatile InetAddress LOCAL_ADDRESS = null;

	/**
	 * valid address
	 * @param address
	 * @return
	 */
	private static boolean isValidAddress(final InetAddress address) {
		if (address == null || address.isLoopbackAddress()) {
			return false;
		}
		final String name = address.getHostAddress();
		return (name != null
				&& ! ANYHOST.equals(name)
				&& ! LOCALHOST.equals(name)
				&& IP_PATTERN.matcher(name).matches());
	}

	/**
	 * get first valid addredd
	 * @return
	 */
	private static InetAddress getFirstValidAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (final Throwable e) {
			logger.error("Failed to retriving ip address, " + e.getMessage(), e);
		}
		try {
			final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					try {
						final NetworkInterface network = interfaces.nextElement();
						final Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									final InetAddress address = addresses.nextElement();
									if (isValidAddress(address)) {
										return address;
									}
								} catch (final Throwable e) {
									logger.error("Failed to retriving ip address, " + e.getMessage(), e);
								}
							}
						}
					} catch (final Throwable e) {
						logger.error("Failed to retriving ip address, " + e.getMessage(), e);
					}
				}
			}
		} catch (final Throwable e) {
			logger.error("Failed to retriving ip address, " + e.getMessage(), e);
		}
		logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
		return localAddress;
	}

	/**
	 * get address
	 * @return
	 */
	public static InetAddress getAddress() {
		if (LOCAL_ADDRESS != null) {
			return LOCAL_ADDRESS;
		}
		final InetAddress localAddress = getFirstValidAddress();
		LOCAL_ADDRESS = localAddress;
		return localAddress;
	}

	/**
	 * get ip
	 * @return
	 */
	public static String getIp(){
		final InetAddress address = getAddress();
		if (address==null) {
			return null;
		}
		return address.getHostAddress();
	}

	/**
	 * get ip:port
	 * @param port
	 * @return
	 */
	public static String getIpPort(final int port) {
		final String ip = getIp();
		if (ip==null) {
			return null;
		}
		return ip.concat(":").concat(String.valueOf(port));
	}

	public static void main(final String[] args) throws UnknownHostException {
		System.out.println(getIp());
		System.out.println(getIpPort(8080));
	}

}
