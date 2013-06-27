# remove VM_RESERVED from source

#PRINC := "${@int(PRINC) + 1}"

do_compile_prepend() {
	sed -e '/VM_RESERVED/d' -i '${S}/packages/ti/sdo/linuxutils/cmem/src/module/cmemk.c'
}
