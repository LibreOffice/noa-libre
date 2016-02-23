/*
 * This file is part of noa-libre.
 *
 * The Contents of this file are made available subject to
 * the terms of GNU Lesser General Public License Version 2.1.
 *
 * GNU Lesser General Public License Version 2.1
 * ========================================================================
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Publi
 * License version 2.1, as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston
 * MA  02111-1307  USA
 *
 * This file incorporates work covered by the following license notice:
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements. See the NOTICE file distributed
 *   with this work for additional information regarding copyright
 *   ownership. The ASF licenses this file to you under the Apache
 *   License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of
 *   the License at http://www.apache.org/licenses/LICENSE-2.0 .
 */

package ag.ion.bion.officelayer.util;

/**
 * WinRegKeyException is a checked exception.
 */
final class WinRegKeyException extends java.lang.Exception {

    /**
     * Constructs a <code>WinRegKeyException</code>.
     */
    public WinRegKeyException() {
        super();
    }

    /**
     * Constructs a <code>WinRegKeyException</code> with the specified
     * detail message.
     *
     * @param  message   the detail message
     */
    public WinRegKeyException( String message ) {
        super( message );
    }
}
