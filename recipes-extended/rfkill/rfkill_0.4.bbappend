# fix version check

#PRINC := "${@int(PRINC) + 1}"

do_configure_append() {
	sed -e 's/desc=.*$/descr="v\$VERSION"/' -i '${S}/packages/ti/sdo/linuxutils/cmem/src/module/cmemk.c'
}
