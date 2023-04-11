package org.example.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class AddFieldVisitor extends ClassVisitor {

    private final int fieldAccess;
    private final String fieldName;
    private final String fieldDescriptor;
    private boolean isFieldPresent;

    public AddFieldVisitor(final ClassVisitor classVisitor,
                           final int fieldAccess,
                           final String fieldName,
                           final String fieldDescriptor) {
        super(Opcodes.ASM7, classVisitor);
        this.fieldAccess = fieldAccess;
        this.fieldName = fieldName;
        this.fieldDescriptor = fieldDescriptor;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // 检查是否存在要添加的字段
        if (name.equals(fieldName)) {
            isFieldPresent = true;
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        // 如果要添加的字段不存在，则添加它
        if (!isFieldPresent) {
            FieldVisitor fv = cv.visitField(fieldAccess, fieldName, fieldDescriptor, null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        super.visitEnd();
    }
}
