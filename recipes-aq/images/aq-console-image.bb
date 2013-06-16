DESCRIPTION = "The most basic Gumstix image"

inherit image
IMAGE_FEATURES += "package-management"
IMAGE_EXTRA_INSTALL ?= ""

DEPENDS = "virtual/kernel"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
 "

AUDIO_INSTALL = " \
 "

BASE_INSTALL = " \
  ${MACHINE_EXTRA_RRECOMMENDS} \
  ${@base_contains("DISTRO_FEATURES", "bluetooth", "bluez4", "", d)} \
  avahi-systemd avahi-utils \
  base-files \
  base-passwd \
  bash \
  coreutils \
  debianutils \
  start-stop-daemon \
  dbus \
  devmem2 \
  man \	
  man-pages \
  memtester \
  netbase \
  ntp-systemd \
  net-tools \
  polkit \
  rsyslog-systemd \
  sed \
  shadow tinylogin \
  systemd systemd-compat-units \
  u-boot-mkimage \
  udisks udisks-systemd \
  upower \
  update-alternatives-cworth \
  util-linux \
  which \
  zypper \
 "

FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-rtl8192cu \
  linux-firmware-rtl8192ce \
  linux-firmware-rtl8192su \
  linux-firmware-wl12xx \
 "

NETWORK_INSTALL = " \
  networkmanager \
  networkmanager-tests \
  rfkill \
  wireless-tools \
  ${@base_contains("DISTRO_FEATURES", "wifi", "iw wpa-supplicant", "", d)} \
 "
#  ifupdown \

TOOLS_INSTALL = " \
  bzip2 \
  cpufrequtils \
  dosfstools \
  e2fsprogs \
  evtest \
  findutils \
  iputils \
  grep \
  gzip \
  htop \
  nano \
  openssh-ssh openssh-keygen openssh-scp openssh-sshd-systemd \
  sudo \
  systemd-analyze \
  tar \
  vim \
  wget \
  zip \
  unzip \
  i2c-tools \
 "

DEV_SDK_INSTALL = " \
   binutils \
   binutils-symlinks \
   cpp \
   cpp-symlinks \
   diffutils \
   file \
   gcc \
   gcc-symlinks \
   g++ \
   g++-symlinks \
   gettext \
   ldd \
   libstdc++ \
   libstdc++-dev \
   libtool \
   make \
   cmake \
   autoconf \
   automake \
   pkgconfig \
   subversion \
   glib-2.0 \
   gtk+ \
   gtk+-dev \
   gst-ffmpeg \
   libav-dev \
   libavcodec-dev \
   libdrm-dev \
 "

EXTRA_TOOLS_INSTALL = " \
    devmem2 \
    ethtool \
    git \
    iperf \
    openssh-ssh openssh-keygen openssh-scp openssh-sshd-systemd \
    sysfsutils \
    tcpdump \
    usbutils \
 "

VIDEO_TOOLS_INSTALL = "\
    libsdl \
    libpng \
    v4l-utils \
    libav \
    gnome-mplayer \
    gstreamer \
    gst-plugins-base \
    gst-plugins-good \
    gst-plugins-good-video4linux2 \
    gst-plugins-base-ximagesink \
    xauth \
"

IMAGE_INSTALL += " \
  ${BASE_INSTALL} \
  ${KERNEL_EXTRA_INSTALL} \
  ${AUDIO_INSTALL} \
  ${FIRMWARE_INSTALL} \
  ${NETWORK_INSTALL} \
  ${ROOTFS_PKGMANAGE} \
  ${TOOLS_INSTALL} \
  ${DEV_SDK_INSTALL} \
  ${EXTRA_TOOLS_INSTALL} \
  ${VIDEO_TOOLS_INSTALL} \
 "

# this section removes remnants of legacy sysvinit support
# for packages installed above
IMAGE_FILE_BLACKLIST += " \
                        /etc/init.d/NetworkManager \
                        /etc/init.d/avahi-daemon \
                        /etc/init.d/dbus-1 \
                        /etc/init.d/dnsmasq \
                        /etc/init.d/networking \
                        /etc/init.d/ntpd \
                        /etc/init.d/sshd \
                        /etc/init.d/udev \
                        /etc/init.d/udev-cache \
                       "

remove_blacklist_files() {
	for i in ${IMAGE_FILE_BLACKLIST}; do
		rm -rf ${IMAGE_ROOTFS}$i
	done
}

rootfs_fixup() {
	# fix broken rsyslogd startup systemd script
	sed -e '/ExecStartPre/d' -i '${IMAGE_ROOTFS}/lib/systemd/system/rsyslog.service'
}

ROOTFS_POSTPROCESS_COMMAND =+ "remove_blacklist_files ; rootfs_fixup ; "

