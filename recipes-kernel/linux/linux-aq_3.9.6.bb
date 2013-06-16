require linux.inc

DESCRIPTION = "Linux kernel for OMAP processors"
KERNEL_IMAGETYPE = "uImage"

COMPATIBLE_MACHINE = "duovero"

BOOT_SPLASH ?= "logo_linux_clut224-generic.ppm"
PV = "3.9.6"

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/bn999/linux-stable;branch=linux-3.9.y;protocol=git \
	   file://defconfig \
           file://${BOOT_SPLASH} \
           "

